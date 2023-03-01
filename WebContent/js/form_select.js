let selectProvincia = document.getElementById("select-provincia");
let selectMunicipio = document.getElementById("select-municipio");

window.onload = fillProvincias(selectProvincia);

selectProvincia.addEventListener("change", function (event) {
  let url =
    "http://localhost:8080/ADD_P2.2_GABRIEL_ZAFRA_LALLANA/geo?provincia=" +
    selectProvincia.value;

  let data = {
    method: "GET",
  };

  fetch(url, data)
    .then(function (response) {
      return response.json();
    })
    .then(function (municipios) {
      console.log(municipios);
    });
});

function fillProvincias(provinciaSelect) {
  let url = "http://localhost:8080/ADD_P2.2_GABRIEL_ZAFRA_LALLANA/geo";

  let data = {
    method: "GET",
  };

  fetch(url, data)
    .then(function (response) {
      return response.json();
    })
    .then(function (provincias) {
      console.log(provincias);
      provincias.forEach(provincia => {
        provinciaSelect.add(new Option(provincia.nm,provincia.id))
      });
    });
}
