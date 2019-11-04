function required(value, controlGroup) {
    if(value == "" || value == null) {
        controlGroup.addClass("error").delay(2000).queue(function(next){
            controlGroup.removeClass("error");
            next();
        });
        return false;
    }
    return true;
}