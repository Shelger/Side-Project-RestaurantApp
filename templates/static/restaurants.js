function getRestaurants() {
    const x = document.getElementById("restaurants");
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
      } else {
        x.innerHTML = "Geolocation is not supported by this browser.";
      }

      function showPosition(position) {
        fetch("/restaurantsData?lat=" + position.coords.latitude + "&lng=" + position.coords.longitude).then(response => response.json()).then((result) => {
            for(let i = 0; i < result.length; i++) {
                const tmp = document.createElement('li');
                tmp.className = 'list-group-item d-flex justify-content-between align-items-center';
                tmp.innerHTML = 'Restaurant:' +
                                    'ID: ' + result[i].id + '<br>' +
                                    'Name: ' + result[i].name + '<br>' +
                                    'Price Level: ' + result[i].price + '<br>' +
                                    'Rating: '+ result[i].rating + '<br>' +
                                    'Location: ' + result[i].location;
                const showInMap = document.createElement('span');
                var path = 'map?lat=' + result[i].lat +"&lng=" + result[i].lng;
                showInMap.innerHTML = '<button class="btn btn-default" onclick="location.href=\'map?lat='
                                                                                                   + result[i].lat + '&lng=' + result[i].lng + '\'">' +'Show In Map</button>';
                tmp.appendChild(showInMap);
                x.appendChild(tmp);
            }
        });
      }
}


//async function getCurLocation() {
//
//      await navigator.geolocation.getCurrentPosition(
//        (position) => {
//          const pos = {
//            lat: position.coords.latitude,
//            lng: position.coords.longitude,
//          };
//          return pos;
//        }
//      );
//}