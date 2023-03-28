package herencia;

public class Carro extends Vehiculo{//Clase hijo
    public  String modelo="Mustang";

    public static void main(String[] args) {
        Carro objCar=new Carro();
        
        System.out.println("Mi carro es de marca "+objCar.marca+ " y su modelo es "+objCar.modelo+
        " y el sonido que emite es: "+ objCar.sonido());
    }
}
