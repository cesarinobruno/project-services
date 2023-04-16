package projectservices.project.model;

import java.util.List;

public class PersonListResult
{
    private List<Person> personList;

    private Integer total;

    public PersonListResult() {}

    public PersonListResult(final List<Person> personList, final Integer total) { this.personList = personList; this.total = total; }

    public List<Person> getPersonList() { return personList; }

    public void setPersonList(final List<Person> personList) { this.personList = personList; }

    public Integer getTotal() { return total; }

    public void setTotal(Integer total) { this.total = total; }
}