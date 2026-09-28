package Assik3.legacy;

public class OldNewspaperPress {
    public int printArticle(String article,int sectionCode){
        if(article == null || article.isBlank()){
            return -2;
        }
        if (sectionCode == 1){
            System.out.println("Новости матча: " + article);
            return 0;
        }
        else if (sectionCode == 2){
            System.out.println("Итоги матча: " + article);
            return 0;
        }
        else{
            return -1;
        }
    }
}
