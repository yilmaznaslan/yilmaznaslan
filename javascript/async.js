fetch("https://api.randomuser.me/?nat=US&results=1")
    .then(res => res.json())
    .then(json => json.results)
    //.then(console.log)
    .catch(console.error);


// Using async
const getFakePersons = async () => {
    try {
        let result = await fetch("https://api.randomuser.me/?nat=US&results=1");
        let {results} = await result.json();
        return results;
    } catch (error) {
        console.error("Error happened to call api", error)
    }

}
getFakePersons()
    .then(person => console.log(person))