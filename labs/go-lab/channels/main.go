package channels

import (
	"fmt"
	"time"
)

func main() {

	go ScanGroups()
	go ScanDiscussions()

		for i := 1; i < 5; i++ {
		fmt.Printf("Waiting before closing the application: %d \n", i)
		time.Sleep(1000 * time.Millisecond)
	}



	
	fmt.Println("Channels Demo")

	// Channels

	// Deadlock
	//deadLockExample()

	// Buffered Channels
	bufferedChannel := make(chan string, 2)

	// Sender
	go func() {
		bufferedChannel <- "Message 1"
		fmt.Printf("Sent message 1 \n")

		bufferedChannel <- "Message 2"
		fmt.Printf("Sent message 2 \n")

		bufferedChannel <- "Message 3"
		fmt.Printf("Sent message 3 \n")

		//close(bufferedChannel)
	}()

	time.Sleep(5 * time.Second)
	// Receiver
	for msg := range bufferedChannel {
		fmt.Printf("Processing message %s \n", msg)
		time.Sleep(500 * time.Millisecond)
	}

	fmt.Println("Done")

}

/*
Sender tries to send and no one is listening, the program stops (blocks) and waits forever.
*/
func deadLockExample() {

	ch := make(chan string)
	ch <- "Hello" //DEADLOCK Example fatal error: all goroutines are asleep - deadlock!

}
