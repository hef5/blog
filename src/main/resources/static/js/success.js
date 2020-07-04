/*using jwt instead of session
store the token in sessionStorage after validating the user password*/

function storeItems(token, userName, userAvatar){

    sessionStorage.setItem('token', token);
    sessionStorage.setItem('userAvatar', userAvatar);
    sessionStorage.setItem('userName', userName);

}
