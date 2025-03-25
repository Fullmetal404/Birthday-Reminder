import java.time.LocalDate;
import java.time.Period;

public class Contact implements Comparable<Contact>
{
    private String name;
    private LocalDate dob;

    /**
     * Contact Constructor
     * @param name The name of the contact
     * @param dob The date of birth of the contact
     */
    public Contact(String name, LocalDate dob) {
        this.name = name;
        this.dob = dob;
    }

    /**
     * getName Accessor Method
     * @return return the name of the contact
     */
    public String getName() {
        return name;
    }
    
    /**
     * getFirstName Accessor Method
     * @return return the first name of the contact
     */
    public String getFirstName()
    {
        String [] lstname = name.split(" ");
        String fname = lstname[0];
        return fname;
    }

   /**
    * getDob Accessor Method
    * @return return the date of birth of the contact
    */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * getAge Accessor Method
     * @return return the age of the contact
     */
    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dob, currentDate).getYears();
    }
    
    public String toString() {
        return(getName() + " " + getDob());
    }

    public int compareTo(Contact other) {
       return other.getAge() - this.getAge();   
    }
}