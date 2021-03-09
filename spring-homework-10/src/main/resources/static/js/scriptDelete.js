async function deleteBook(){
    let url = '/api/v1/book/' + document.querySelector("#id-input").value;

    try{
        let res = await fetch(url, {
          method: 'DELETE',
          headers: {"Content-Type": "application/json",
            },
          }
        );
        await res.json();
        } catch (error){
          console.log(error);
        }
        window.location.href = '/';
}
var authors;
var genres;
renderBook();