
async function save(){
    let url = '/api/v2/book';

    var book = {
        'id': document.querySelector("#id-input").value,
        'name': document.querySelector("#name").value,
        'author': getSelectedAuthor(),
        'genre': getSelectedGenres()
    }
    let bookDto = JSON.stringify(book);

    try{
        let res = await fetch(url, {
          method: 'PUT',
          body: bookDto,
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