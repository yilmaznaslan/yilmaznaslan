package main

import (
	"encoding/json"
	"fmt"
	"hello-go/models"
	"net/http"
)

func main() {

	http.HandleFunc("/", handleHome)
	http.HandleFunc("/users", getUsers)



	fmt.Println("Server running on port 8080")
	http.ListenAndServe(":8080", nil)


}

func handleHome(w http.ResponseWriter, r *http.Request ) {
	fmt.Println(r.Header.Get("user-agent"))
	fmt.Println("Handling request")
	w.Write([]byte("Hello"))
}

func getUsers(w http.ResponseWriter, r *http.Request) {

	user1 := models.User {
		Name: "user1",
		Email: "yilmazn.aslan@gmail.com",
	}

	user2 := models.User {
		Name: "user2",
		Email: "yilmazn.aslan@gmail.com",
	}

	users := [] models.User {user1, user2}


	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(users)

}
