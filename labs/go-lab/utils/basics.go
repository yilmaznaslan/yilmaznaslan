package utils

import (
	"fmt"
	"hello-go/models"
)

func test() {

	sayHello()
	fmt.Println("I am learning Go")

	var message = buildWelcomeMessage("yilmaz", 32)
	fmt.Println(message)

	var name string = "yilmaz"
	age := 33

	fmt.Printf("Name is %s and my age is %d", name, age)

	var lastname string
	var experienceYear int

	fmt.Printf("Lastname %s, and initial age %d.\n", lastname, experienceYear)

	const nation = "Turkey & German \n"
	fmt.Print(nation)

	userName, userAge := getUserInfo(1)
	fmt.Printf("Fetched user from db : %s id: %d\n", userName, userAge)

	var primaryCountryName, _ = getUserNation()
	fmt.Printf("Primary country %s \n", primaryCountryName)

	if _, age := getUserInfo(1); age < 10 {
		fmt.Println("Your are lucky")
	} else {
		fmt.Println("You are old")
	}

	for i := 0; i < 10; i++ {
		fmt.Printf("Hello %d \n", i)
	}

	j := 3
	for j < 10 {
		fmt.Printf("While loop %d \n", j)
		j++
	}

	grades := []int{12, 5}
	for index, grade := range grades {
		fmt.Println(index, grade)
	}

	lastName := "Aslan Ciloglu"
	for _, letter := range lastName {
		fmt.Println(letter)
	}

	user := models.User{Name: "Yilmaz", Email: "yilmazn.aslan@gmail.com", Age: 33}
	fmt.Println(user.Email)
}

func sayHello() {
	fmt.Println("Hello World")
}

func buildWelcomeMessage(name string, age int) string {
	return fmt.Sprintf("Name: %s, age: %d", name, age)
}

func getUserInfo(userAge int) (string, int) {

	fmt.Printf("Getting user data from db by userAge:%d \n", userAge)
	return "Yilmaz Naci Aslan", 5
}

func getUserNation() (string, string) {
	return "Turkey", "Germany"

}
