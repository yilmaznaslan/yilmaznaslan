package channels

import (
	"fmt"
	"time"
)

func ScanGroups() {

	fmt.Println("Starting to scan reddits, it will take a while")
	for i := 1; i < 10; i++ {
		fmt.Printf("Checking page: %d \n", i)
		time.Sleep(500 * time.Millisecond)
	}
}

func ScanDiscussions() {
	fmt.Println("Scanning discussion groups")
	for i := 0; i < 5; i++ {
		fmt.Printf("Scanning discussion groups :%d \n", i)
		time.Sleep(500 * time.Millisecond)
	}
}
