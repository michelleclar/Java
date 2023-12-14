@RestController
@RequestMapping("/hello")
public class HelloController {
  @GetMapping("/welcome")
  public String welcome(){
    return "Hello World"
  }
}
