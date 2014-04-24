jQuery.expr[':'].Contains = function(a,i,m){ return (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase())>=0; };

function FilterCriteria(textbox){
    var filter = $(textbox).val();
    var attributeList = $(textbox).next().children(':first');
    if(filter) {
        $(attributeList).find("li:not(:Contains(" + filter + "))").slideUp();
        $(attributeList).find("li:Contains(" + filter + ")").slideDown();
    }else {
        $(attributeList).find("li").slideDown();
    }

}

isObject = function(a) {
    return (!!a) && (a.constructor === Object);
};

function isInt(n) {
   return n % 1 === 0;
}