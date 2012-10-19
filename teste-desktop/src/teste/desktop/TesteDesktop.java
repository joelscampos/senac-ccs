package teste.desktop;

public class TesteDesktop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int idade = 68;
        System.out.println((4>>3)); 
        System.out.println((2<<3)^(4>>3));  
        System.out.println((2<<3)^(4<<3));  
        
        System.out.println((2<3)^(4>3));  
        System.out.println((2<3)^(4<3));
        
        if(idade == 68 & ++idade == 69 ) {
            
            System.out.println(idade);
        }
    }
    
}
