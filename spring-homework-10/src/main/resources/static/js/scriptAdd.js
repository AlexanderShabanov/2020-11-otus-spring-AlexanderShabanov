async function renderBook() {
    authors = await getAllAuthors();
    genres = await getAllGenres();
    let authorSelect = document.querySelector("#author");

    authors.map(author => {
      var opt = document.createElement('option');
      opt.value = author.id;
      opt.innerHTML = author.name + ' ' + author.surName;
      authorSelect.appendChild(opt);
    }
    );
    let genreSelect = document.querySelector("#genre-input");
    genres.map(genre => {
      var opt = document.createElement('option');
      opt.value = genre.id;
      opt.innerHTML = genre.name;
      genreSelect.appendChild(opt);
    }
    );

}
async function addBook(){
    let url = '/api/v1/book';

    var book = {
        'name': document.querySelector("#name").value,
        'author': getSelectedAuthor(),
        'genre': getSelectedGenres()
    }
    let bookDto = JSON.stringify(book);

    try{
        let res = await fetch(url, {
          method: 'POST',
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