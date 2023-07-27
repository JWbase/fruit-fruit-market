$(document).on('click', '#buttonDb', () => {
    axios({
        method: 'get',
        url: '/user/testSelect'
    })
        .then(response => {
            const data = response.data;
            const dataDiv = document.getElementById('dataDiv');

            for (let key in data) {
                let value = data[key];
                let dataElement = document.createElement('p');
                dataElement.textContent = key + ' : ' + value;
                dataDiv.appendChild(dataElement);
            }
        });
})