package dev.tg;

public class Main {
    //static Rolls rolls = new Rolls(); //creates Category map, grants access to roll methods

    static Challenge test = new Challenge();

    public static void main(String[] args) {
        //Window.open();

        //rolls.masterCatMap.forEach( (k,v) -> System.out.println(v) );  //category map loads correctly.

        //test.runDefaultChallenge().forEach(System.out::println); this sometimes causes program to hangup, not sure of source

//        for(int i = 0; i < 1000; i++) {
//            rolls.rollSelection(rolls.rollCategory());
//        }

//        BufferedWriter writer = null;
//        try {
//            writer = new BufferedWriter(new FileWriter("output.txt"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            writer.write("Hello Test");
//            writer.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        //System.out.println(test.rollMemory);
        //test.saveMemory();
        //test.clearRollMemory();
        //System.out.println(test.rollMemory);


//        File file = new File("./res/dataStorage/CategoryMap.csv");
//        System.out.println(file.getAbsoluteFile().exists());


//        HashMap<String,Category> mapTest = new HashMap<>();
//        mapTest.put("test", new Category("test", new ArrayList<>()));
//        Selection ohBoy = new Selection("test", "ohBoy", 2);
//        List<Selection> changeMe = mapTest.get("test").category();
//        changeMe.add(ohBoy);
//        System.out.println(mapTest);
        //above tests and confirms mutability of Selection List inside the Category inside the hashmap

    }



}
