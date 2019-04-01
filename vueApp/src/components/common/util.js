import axios from 'axios';
export default {
    dictParse(dictID,dict){
        let dictName = '';
        for (let i=0;i<dict.length;i++){
            if(dict[i].ctdm == dictID){
                dictName = dict[i].ctmc;
            }
        }
        return dictName;
    },
};