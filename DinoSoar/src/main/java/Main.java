import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.smartcar.sdk.*;
import com.smartcar.sdk.data.*;

import org.eclipse.jetty.websocket.common.events.ParamList;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
  // global variable to save our accessToken
  private static String access;
  private static Gson gson = new Gson();

  public static void main(String[] args)
  {

    port(8000);

      AuthClient client = new AuthClient(
              "22c4d5a8-80fc-4b04-9715-51e180b6fc0f",
              "7ee906ef-1c50-4e10-ac00-1909e8f18811",
              "http://localhost:8000/exchange",
              true
      );


      get("/login", (req, res) ->
      {
          String link = client.getAuthUrl();
          res.redirect(link);
          return null;
      });

      get("/exchange", (req, res) -> {
         String code = req.queryMap("code").value();

         // TODO: Request Step 1: Obtain an access token
         Auth auth = client.exchangeCode(code);

         // in a production app you'll want to store this in some kind of persistent storage
          access = auth.getAccessToken();

                  return "";
              });

      get("/vehicle", (req, res) -> {
          SmartcarResponse<VehicleIds> vehicleIdResponse = AuthClient.getVehicleIds(access);
          // the list of vehicle ids
          String[] vehicleIds = vehicleIdResponse.getData().getVehicleIds();


            Vehicle vehicle = new Vehicle(vehicleIds[0], access);
            //Vehicle General Info
            VehicleInfo info = vehicle.info();
            String infoToJson = gson.toJson(info);
            JsonObject json = new JsonParser().parse(infoToJson).getAsJsonObject();   //Parsing Object

            String make = json.get("make").getAsString();
            String model = json.get("model").getAsString();
            String year = json.get("year").getAsString();

            SmartcarResponse odometer = vehicle.odometer();
            String odometerToString = odometer.getData().toString();
            String distance = jsonToString(odometerToString);

            Car myTesla = new Car(make, model, year, distance);
            drawCar(myTesla);



          res.type("application/json");
          return "Make: " + make + " Model: " + model + " Year: " + year + " Distance: " + distance;
      });
  }

  public static String jsonToString(String json)
  {
      String[] split = json.split("=");
      return split[1].substring(0, split[1].length() - 1);
  }

  public static void drawCar(Car car)
  {
      JFrame frame = new JFrame(car.make + " " + car.model);
      Canvas canvas = new Drawing(car);
      canvas.setSize(1200, 670);
      frame.add(canvas);
      frame.pack();
      frame.setVisible(true);
  }


//  public static void animate(Car myTesla)
//  {
//      JFrame frame = new JFrame(myTesla.make + " " + myTesla.model);
//      JPanel screen = new Drawing(myTesla);
//      frame.add(screen);
//      screen.setSize(1200, 670);
//      frame.setVisible(true);
//      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//  }

}
