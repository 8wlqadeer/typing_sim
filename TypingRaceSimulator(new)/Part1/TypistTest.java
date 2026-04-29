package Part1;

public class TypistTest 
{
    public static void main(String[] args)
    {
        Typist t1 = new Typist('@',"Leena",0.3);
        //each time a character is typed typist progresses through passage.
         t1.typeCharacter();  //sets progress to 1
         t1.typeCharacter(); // progress to 2
         t1.typeCharacter(); // progress to 3
         System.out.println(t1.getProgress()); //returns 3


         

    }
    
    
}
