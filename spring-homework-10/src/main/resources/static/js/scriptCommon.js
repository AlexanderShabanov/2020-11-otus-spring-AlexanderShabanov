async function getBook(id) {
    let url = '/book/'+id;
    try{
        let res = await fetch(url);
        return await res.json();
        } catch (error){
          console.log(error);
        }
}
async function renderBook() {
    authors = await getAllAuthors();
    genres = await getAllGenres();
    let id = document.querySelector("#id-input").value;
    let book = await getBook(id);
    document.querySelector("#name").value = book.name;
    let authorSelect = document.querySelector("#author");
    authorSelect.field = book.author.id;

    authors.map(author => {
      var opt = document.createElement('option');
      opt.value = author.id;
      opt.innerHTML = author.name + ' ' + author.surName;
      if( author.id == book.author.id )
        opt.selected = true;
      authorSelect.appendChild(opt);
    }
    );
    let genreSelect = document.querySelector("#genre-input");
    genreSelect.field=book.genre;
    genres.map(genre => {
      var opt = document.createElement('option');
      if(book.genre.map(function(g) {return g.id;}).includes(genre.id)){
        opt.selected = true;

      }
      opt.value = genre.id;
      opt.innerHTML = genre.name;
      genreSelect.appendChild(opt);
    }
    );

}
async function getAllAuthors() {
    let url = '/author';
    try{
        let res = await fetch(url);
        return await res.json();
        } catch (error){
          console.log(error);
        }
}
async function getAllGenres() {
    let url = '/genre';
    try{
        let res = await fetch(url);
        return await res.json();
        } catch (error){
          console.log(error);
        }
}
function getSelectedGenres(){
    var genreArray = [];
    var selectedOptions = document.querySelector("#genre-input").selectedOptions;
    for (let i=0; i < selectedOptions.length; i++){
        genreArray.push(genres.find(function(a){return a.id == selectedOptions[i].value}));
    }
    return genreArray;
}
function getSelectedAuthor(){
    var selectedOptions = document.querySelector("#author").selectedOptions;
    return authors.find(function(a){return a.id == selectedOptions[0].value});
}