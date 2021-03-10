async function getBooks() {
    let url = '/api/v2/book';
    try{
        let res = await fetch(url);
        return await res.json();
        } catch (error){
          console.log(error);
        }
}
async function renderBooks() {
    let books = await getBooks();
    let html = '';
    books.map(book => {
        let htmlSegment =  `<tr class="bookRow" onclick = "renderComments(event, \'${book.id}\')">
                                <td>${book.id}</td>
                                <td>${book.name}</td>
                                <td>
                                    <table style="border:none; padding: 1px;">`;
        book.genre.forEach( function ( currentGenre ){
                                          htmlSegment += `<tr>
                                            <td style="border:none; padding: 1px;">${currentGenre.name}</td>
                                        </tr>`;
                                          }
                                          );
        htmlSegment += `</table>
                                </td>
                                <td>${book.author.name} ${book.author.surName}</td>`;
        htmlSegment +=`<td style="border:none;">
                    <a href="/editBook?id=${book.id}">
                        <img src="img/edit.png" width="15" height="15" alt="Редактировать">
                    </a>
                </td>
                <td style="border:none;">
                    <a href="/deleteBook?id=${book.id}">
                        <img src="img/delete.png" width="15" height="15" alt="Удалить">
                    </a>
                </td>
            </tr>`;

        html += htmlSegment;
    });
    document.querySelector("#bookTable > tbody:nth-child(2)").innerHTML = html;
    document.querySelector("#comments").width = "800px";
}
async function renderComments(event, bookId){
        if(event.currentTarget.className == "bookRow"){
        let bookRows = event.currentTarget.parentElement.rows;
        for (i=0; i< bookRows.length; i++){
            if(bookRows[i].highlight == true){
                bookRows[i].style.backgroundColor = bookRows[i].origColor;
                bookRows[i].highlight = false;
            }
        }
        highlight(event.currentTarget);
        let comments = await getComments(bookId);
        let commentTbody = document.querySelector("#comments tbody");
        while(commentTbody.rows.length > 0){
            commentTbody.deleteRow(0);
        }
        comments.map(comment =>{
            let newRow = commentTbody.insertRow();
            newRow.innerHTML = `<td width = 50px>${comment.id}</td>
                                <td width = 20px>${comment.user.name}</td>
                                <td>${comment.commentText}</td>`;
        });
        event.stopPropagation();
    }
}
async function getComments(bookId){
let url = '/api/v2/book/'+bookId+'/comment';
    try{
        let res = await fetch(url);
        return await res.json();
        } catch (error){
          console.log(error);
        }

}
function highlight(row){
    row.origColor=row.style.backgroundColor;
    row.style.backgroundColor='#BCD4EC';
    row.highlight = true;
}
renderBooks();