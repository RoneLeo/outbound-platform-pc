import axios from 'axios';
export default {
    dictParse(dictID,dictArr){
        let dictName = '';
        for (let i=0;i<dictArr.length;i++){
            if(dictArr[i].ctdm == dictID){
                dictName = dictArr[i].ctmc;
            }
        }
        return dictName;
    },
};