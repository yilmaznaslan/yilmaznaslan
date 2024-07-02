# README

### 0. Connect to EC2 instance
```bash
ssh -i work_mac_os_2024.pem ubuntu@ec2-3-219-34-188.compute-1.amazonaws.com
```

### 1. Install Nvidia driver

```bash
sudo apt-get update
sudo apt install build-essential -y
wget https://us.download.nvidia.com/XFree86/Linux-x86_64/525.85.05/NVIDIA-Linux-x86_64-525.85.05.run
chmod +x NVIDIA-Linux-x86_64-525.85.05.run
sudo ./NVIDIA-Linux-x86_64-525.85.05.run
nvidia-smi
```

Verify the installation by running `nvidia-smi`

### 2. Pull the docker image

Authenticate with Nvidia NGC (https://ngc.nvidia.com/signin)

```
docker login nvcr.io

Username: $oauthtoken
Password: anZsaWl0amFyODl2bzQ4ZjdhbWJpMGxrYzphMmUwYTZlOS1iZTI1LTQwYTgtOGIzNi1mMzJiN2ZmMTNkYmE
```

Pull the docker image

```bash
docker pull nvcr.io/nvidia/isaac-sim:2023.1.1
```

### 3. Run the Isaac Sim docker container

```bash
docker run --name isaac-sim --entrypoint bash -it --gpus all -e "ACCEPT_EULA=Y" --rm --network=host \
    -e "PRIVACY_CONSENT=Y" \
    -v ~/docker/isaac-sim/cache/kit:/isaac-sim/kit/cache:rw \
    -v ~/docker/isaac-sim/cache/ov:/root/.cache/ov:rw \
    -v ~/docker/isaac-sim/cache/pip:/root/.cache/pip:rw \
    -v ~/docker/isaac-sim/cache/glcache:/root/.cache/nvidia/GLCache:rw \
    -v ~/docker/isaac-sim/cache/computecache:/root/.nv/ComputeCache:rw \
    -v ~/docker/isaac-sim/logs:/root/.nvidia-omniverse/logs:rw \
    -v ~/docker/isaac-sim/data:/root/.local/share/ov/data:rw \
    -v ~/docker/isaac-sim/documents:/root/Documents:rw \
    nvcr.io/nvidia/isaac-sim:2023.1.1
```

### 4.Start Isaac sim in native livestream mode

To use the Omniverse Streaming Client,
```bash
./runheadless.native.sh -v
```

```bash
./runheadless.native.sh -v --allow-root --/app/window/drawMouse=false
```

To use the WebRTC Browser Client
```bash
./runheadless.webrtc.sh
```
http://localhost:8211/streaming/webrtc-client?server=100.26.251.126


http://100.26.251.126:8211/streaming/webrtc-client?server=100.26.251.126

ssh -i work_mac_os_2024.pem -L 8211:localhost:8211 ubuntu@ec2-100-26-251-126.compute-1.amazonaws.com

- http://ec2-100-26-251-126.compute-1.amazonaws.com:8211/streaming/webrtc-client

- http://localhost:8211/streaming/webrtc-client?server=localhost

- http://localhost:8211/streaming/webrtc-client



To use the WebSocket Browser Client
```bash
./runheadless.websocket.sh
```

http://ec2-100-26-251-126.compute-1.amazonaws.com:8211/streaming/client

ssh -i your_key.pem -L 8080:localhost:80 ec2-user@your_ec2_public_dns
