
// Since functions are variables, we can add them to variables
const dog = {
    'name': "Tom",
    whisper(dogname) {
        console.log(`Piu ${dogname}`)
    }
}

dog.whisper(dog.name)