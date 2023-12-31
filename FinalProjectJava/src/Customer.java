import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = -450981020981188647L;
    private String name;
    private String email;

    public Customer(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Email: " + email;
    }

    public boolean equals(Customer customer){
        return (Objects.equals(this.name, customer.getName()) && Objects.equals(this.email, customer.getEmail()));

    }
}
