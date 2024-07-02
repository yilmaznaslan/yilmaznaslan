# Installation

Install HostAPD and DNSMasq:
````bash
sudo apt install hostapd dnsmasq
````



```bash
sudo nano /etc/dhcpcd.conf
```
Note that file was empty
interface wlan0
static ip_address=192.168.4.1/24
nohook wpa_supplicant



`sudo nano /etc/hostapd/hostapd.conf`
sudo cat /etc/hostapd/hostapd.conf
file was empty

interface=wlan0
driver=nl80211
ssid=rpi
hw_mode=g
channel=11
wmm_enabled=0
macaddr_acl=0
auth_algs=1
ignore_broadcast_ssid=0
wpa=2
wpa_passphrase=test1234
wpa_key_mgmt=WPA-PSK
wpa_pairwise=TKIP
rsn_pairwise=CCMP



## Startes
sudo systemctl restart hostapd


systemctl status hostapd.service




## Enabling ip forwarding



## Start
sudo systemctl start hostapd
sudo systemctl start dnsmasq


sudo systemctl enable hostapd
sudo systemctl enable dnsmasq



# It works now

pi@raspberrypi:~ $ sudo ip route
default via 192.168.178.1 dev wlan0 proto dhcp src 192.168.178.64 metric 303 mtu 1500 
192.168.178.0/24 dev wlan0 proto dhcp scope link src 192.168.178.64 metric 303 mtu 1500 
192.168.220.0/24 dev eth0 proto dhcp scope link src 192.168.220.1 metric 202 


before when it was not working
pi@raspberrypi:~ $ sudo ip route
default via 192.168.220.0 dev eth0 src 192.168.220.1 metric 202 
default via 192.168.178.1 dev wlan0 proto dhcp src 192.168.178.64 metric 303 mtu 1500 
192.168.178.0/24 dev wlan0 proto dhcp scope link src 192.168.178.64 metric 303 mtu 1500 
192.168.220.0/24 dev eth0 proto dhcp scope link src 192.168.220.1 metric 202 
