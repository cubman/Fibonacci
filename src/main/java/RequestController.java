import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/Fibonacci")
public class RequestController {
@Inject
    private Fibonacci fibonacci = new Fibonacci(1000);

    @Path("/:num")
    @GET
    public JsonArray getRes(int num) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        try {
            List<Integer> res = fibonacci.getNElemens(num);
            for (int i = 0; i < res.size(); ++i)
              jsonArrayBuilder.add(Json.createObjectBuilder().add((i + 1) + " Fib:", res.get(i)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonArrayBuilder.build();
    }



}
