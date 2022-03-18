package projectservices.project.model;

public class Feedback
{
    private Integer id;
    private String message;
    private Person personId;

    public Feedback(){}

    public Feedback(Integer id, String message, Person personId)
    {
        this.id = id;
        this.message = message;
        this.personId = personId;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Person getPersonId()
    {
        return personId;
    }

    public void setPersonId(Person personId)
    {
        this.personId = personId;
    }
}
