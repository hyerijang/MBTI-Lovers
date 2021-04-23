
/**
 * select -> option 자동으로 만들기
 */
function createSelectBoxAndOptions(selectId, listData) {
  var select = document.getElementById(selectId);
  var user_gender = document.getElementById("user_gender");
  if(user_gender)
    console.log(user_gender.value);
  for (var i in listData) {
    var option = document.createElement("option");
    option.text = listData[i].value;
    select.appendChild(option);
    if(select[i].value  == user_gender.value)
    {
      select[i].selected = true;
    }
  }

}

var listData = [
  {
    value: "남자",
  },

  {
    value: "여자",
  }
];

console.log(listData);
createSelectBoxAndOptions("gender", listData)
