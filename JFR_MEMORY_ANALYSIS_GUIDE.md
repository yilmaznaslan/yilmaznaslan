# Java Flight Recorder (JFR) - Memory Analysis Guide

## What is Java Flight Recorder?

Java Flight Recorder (JFR) is a profiling and event collection framework built into the JVM. It provides low-overhead data collection about the JVM and Java application, including:

- **Memory allocation and consumption**
- **Garbage collection events**
- **Thread activity**
- **Method profiling**
- **I/O operations**
- **Lock contention**

JFR has minimal performance impact (typically <1% overhead) and is production-safe.

## Key Concepts

### Events
JFR records **events** that happen in the JVM:
- **Allocation events**: When objects are allocated
- **GC events**: Garbage collection cycles
- **Memory events**: Heap usage, metaspace usage
- **Thread events**: Thread starts, stops, blocking

### Recording
A **recording** is a time-bounded collection of events saved to a `.jfr` file.

### Profiling
JFR can profile your application to identify:
- Memory hotspots (where objects are allocated)
- Memory leaks (objects that aren't garbage collected)
- GC pressure (frequent collections)

---

## Enabling JFR

### Method 1: Command Line (Recommended for Learning)

```bash
java -XX:+FlightRecorder \
     -XX:StartFlightRecording=duration=60s,filename=recording.jfr \
     -cp your-classpath \
     YourMainClass
```

**Parameters:**
- `-XX:+FlightRecorder`: Enables JFR (required)
- `-XX:StartFlightRecording`: Starts recording immediately
  - `duration=60s`: Record for 60 seconds
  - `filename=recording.jfr`: Output file name

### Method 2: JVM Arguments (Continuous Recording)

```bash
java -XX:+FlightRecorder \
     -XX:StartFlightRecording=duration=0,filename=recording.jfr \
     -cp your-classpath \
     YourMainClass
```

- `duration=0`: Record until JVM exits
- Useful for capturing the entire application lifecycle

### Method 3: Programmatic API (Java 11+)

```java
import jdk.jfr.*;

public class JFRExample {
    public static void main(String[] args) throws Exception {
        Recording recording = new Recording();
        recording.start();
        
        // Your application code here
        
        recording.stop();
        recording.dump(Paths.get("recording.jfr"));
    }
}
```

---

## Analyzing Memory Consumption

### Step 1: Create a Recording

Run your application with JFR enabled:

```bash
./gradlew runWithJFR
```

This will create a `recording.jfr` file.

### Step 2: View the Recording

#### Option A: JDK Mission Control (JMC) - GUI Tool

1. **Install JMC** (if not already installed):
   ```bash
   # On macOS with Homebrew
   brew install jmc
   
   # Or download from: https://adoptium.net/jmc/
   ```

2. **Open the recording**:
   ```bash
   jmc recording.jfr
   ```

3. **Navigate to Memory views**:
   - **Memory** tab → See heap usage over time
   - **GC Configuration** → See GC settings and events
   - **Allocations** → See where objects are allocated

#### Option B: jfr Command Line Tool (Java 14+)

```bash
# Print summary
jfr print recording.jfr

# Print specific events
jfr print --events jdk.ObjectAllocationInNewTLAB recording.jfr

# Print memory events
jfr print --events jdk.GCHeapSummary recording.jfr
```

#### Option C: jcmd (Built-in)

```bash
# Start recording
jcmd <pid> JFR.start name=myrecording duration=60s filename=recording.jfr

# Stop recording
jcmd <pid> JFR.stop name=myrecording

# Dump recording
jcmd <pid> JFR.dump name=myrecording filename=recording.jfr
```

---

## Key Memory Events to Analyze

### 1. Heap Usage (`jdk.GCHeapSummary`)

Shows heap size and usage over time:
- **Used heap**: How much memory is actually used
- **Committed heap**: How much memory is committed to the heap
- **Max heap**: Maximum heap size

**What to look for:**
- Heap usage growing continuously (potential memory leak)
- Heap usage near maximum (need to increase heap size)
- Frequent GC cycles (GC pressure)

### 2. Object Allocation (`jdk.ObjectAllocationInNewTLAB`)

Shows where objects are allocated:
- **Allocation class**: Which class is being allocated
- **Allocation size**: Size of allocated objects
- **Thread**: Which thread allocated the object

**What to look for:**
- Classes with high allocation rates
- Large objects being allocated frequently
- Allocation hotspots

### 3. GC Events (`jdk.G1GarbageCollection`, `jdk.ParallelGarbageCollection`, etc.)

Shows garbage collection activity:
- **GC duration**: How long GC takes
- **GC type**: Young GC, Full GC, etc.
- **Memory reclaimed**: How much memory was freed

**What to look for:**
- Long GC pauses (affects application responsiveness)
- Frequent Full GCs (indicates memory pressure)
- GC taking too long (may need to tune GC settings)

### 4. Memory Leaks (`jdk.OldObjectSample`)

Shows old objects that might be leaks:
- **Object age**: How long the object has been alive
- **Allocation stack trace**: Where the object was allocated
- **Object type**: What type of object it is

**What to look for:**
- Objects that should have been collected but weren't
- Growing number of old objects

---

## Practical Example: Analyzing Memory Consumption

### Example 1: Memory-Intensive Application

See `MemoryIntensiveExample.java` for a sample application that:
- Creates many objects
- Simulates memory pressure
- Demonstrates memory allocation patterns

### Running the Example

```bash
# Run with JFR for 30 seconds
./gradlew runMemoryIntensiveWithJFR

# This creates: build/jfr/memory-intensive-recording.jfr
```

### Analyzing the Results

1. **Open in JMC**:
   ```bash
   jmc build/jfr/memory-intensive-recording.jfr
   ```

2. **Check Memory tab**:
   - See heap usage graph
   - Identify when memory spikes occur
   - Check if heap usage stabilizes or keeps growing

3. **Check Allocations tab**:
   - See which classes allocate the most memory
   - Identify allocation hotspots
   - Check allocation rates

4. **Check GC tab**:
   - See GC frequency and duration
   - Identify if GC is under pressure
   - Check if Full GCs are occurring

---

## Common Memory Issues and How JFR Helps

### Issue 1: Memory Leak

**Symptoms:**
- Heap usage continuously grows
- Frequent Full GCs
- OutOfMemoryError eventually

**How JFR helps:**
- `jdk.OldObjectSample` shows objects that should have been collected
- Allocation events show where problematic objects are created
- Heap summary shows continuous growth pattern

**Example analysis:**
```bash
jfr print --events jdk.OldObjectSample recording.jfr | head -50
```

### Issue 2: High Memory Allocation Rate

**Symptoms:**
- High GC frequency
- Application slowdowns
- High CPU usage from GC

**How JFR helps:**
- Allocation events show which classes allocate most
- Identify allocation hotspots
- Find opportunities to reduce allocations

**Example analysis:**
```bash
jfr print --events jdk.ObjectAllocationInNewTLAB recording.jfr | \
  grep -E "class|size" | sort -k2 -n
```

### Issue 3: Large Object Allocations

**Symptoms:**
- Sudden heap usage spikes
- Long GC pauses
- Direct allocation to old generation

**How JFR helps:**
- Allocation events show large objects
- Identify what creates large objects
- Find opportunities to reduce object size

---

## JFR Event Types for Memory Analysis

| Event | Description | Use Case |
|-------|-------------|----------|
| `jdk.GCHeapSummary` | Heap size and usage | Monitor heap growth |
| `jdk.ObjectAllocationInNewTLAB` | Object allocations | Find allocation hotspots |
| `jdk.OldObjectSample` | Old object samples | Detect memory leaks |
| `jdk.G1GarbageCollection` | G1 GC events | Analyze GC performance |
| `jdk.ParallelGarbageCollection` | Parallel GC events | Analyze GC performance |
| `jdk.MetaspaceSummary` | Metaspace usage | Monitor class metadata |
| `jdk.Compilation` | JIT compilation | See compilation impact |

---

## Best Practices

### 1. Record Duration
- **Short recordings (30-60s)**: For quick analysis, allocation hotspots
- **Long recordings (5-10 min)**: For memory leaks, long-running issues
- **Continuous recording**: For production monitoring

### 2. Event Selection
- Enable only events you need to reduce overhead
- For memory analysis, focus on allocation and GC events

### 3. Production Use
- JFR has low overhead (<1%), safe for production
- Use continuous recording with rotation
- Set up alerts based on JFR data

### 4. Analysis Workflow
1. **Start with overview**: Check heap usage graph
2. **Identify patterns**: Look for spikes, continuous growth
3. **Drill down**: Check specific events related to patterns
4. **Take action**: Fix issues, optimize code

---

## Advanced: Custom Events

You can create custom JFR events in your code:

```java
import jdk.jfr.*;

@Label("Custom Memory Event")
@Description("Tracks custom memory operations")
public class CustomMemoryEvent extends Event {
    @Label("Operation Type")
    public String operationType;
    
    @Label("Memory Size")
    public long memorySize;
}

// Usage
CustomMemoryEvent event = new CustomMemoryEvent();
event.operationType = "cache-load";
event.memorySize = cacheSize;
event.commit();
```

---

## Resources

- [Oracle JFR Documentation](https://docs.oracle.com/javacomponents/jmc-5-5/jfr-runtime-guide/about.htm)
- [JFR Event Reference](https://bestsamplecode.com/jfr-event-reference/)
- [JDK Mission Control](https://adoptium.net/jmc/)

---

## Quick Reference Commands

```bash
# Run with JFR (30 seconds)
java -XX:+FlightRecorder \
     -XX:StartFlightRecording=duration=30s,filename=recording.jfr \
     -cp build/classes/java/main \
     com.yilmaznaslan.threads.jfr.MemoryIntensiveExample

# View recording summary
jfr print recording.jfr

# View specific events
jfr print --events jdk.GCHeapSummary recording.jfr

# Open in JMC
jmc recording.jfr

# Start recording on running process
jcmd <pid> JFR.start name=myrecording duration=60s filename=recording.jfr

# Stop and dump recording
jcmd <pid> JFR.stop name=myrecording
jcmd <pid> JFR.dump name=myrecording filename=recording.jfr
```

