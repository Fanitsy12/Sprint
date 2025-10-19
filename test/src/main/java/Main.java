import com.myframework.core.Test;
import com.myframework.core.Info;

public class Main {
    public static void main(String[] args) {
     
     Class<Test> cls = Test.class;
if(cls.isAnnotationPresent(Info.class)) {
    Info info = cls.getAnnotation(Info.class);
    System.out.println("Auteur : " + info.auteur());
    System.out.println("Version : " + info.version());
    System.out.println("Projet : " + info.projet());
    System.out.println("UID : " + info.uid());
    System.out.println("Date : " + info.date());
    System.out.println("Contact : " + info.contact());
    
}else {
            System.out.println("Annotation Info non trouvée !");
        }
    }
}
