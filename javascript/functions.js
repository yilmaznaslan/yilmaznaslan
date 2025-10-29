sayMyName("Yilmaz") // Function declarations are hoisted -> they can be invoked before the function is created

//sayMyLastName("Aslan") // This will cause error

function sayMyName(name) {
    console.log(`My first name is ${name}`)
}


const sayMyLastName = (name) => {
    var msg = `My last name is: ${name}`
    return msg;
}

// Function returns
console.log(sayMyLastName("Aslan"))

// Arrow functions
const logHello = name => console.log(`Hello  ${name}`)
logHello("Yilmaz")

const sayBye = (firstname, lastname) => console.log(`Bye ${firstname} ${lastname}`)
sayBye("Yilmaz", "Aslan")

const logPrice = price => {
    const newPrice = price + 2;
    console.log(`Original price: ${price}$, new price: ${newPrice}$`)
}
logPrice(10)

// Returning objects
const recipe = (recipeUrl, recipeDescription) => ({
    'recipe_url': recipeUrl,
    'recipe_description': recipeDescription,
    'author': 'Yilmaz naci Aslan'
})
console.log(recipe("www.youtube.com", "Chicken breast with ..."))