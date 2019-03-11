
export default {
    dictParse(dictID,dict){
        let dictName = '';
        for (let i=0;i<dict.length;i++){
            if(dict[i].id == dictID){
                dictName = dict[i].name;
            }
        }
        return dictName;
    }
};