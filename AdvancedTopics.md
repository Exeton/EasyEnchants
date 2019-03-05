# Advanced Topics
## Dependency Swapping
EasyEnchants allows you to swap out its' default dependencies with your own. This includes:
- IEnchantInfoParser
- IEnchantReadWriter
- EnchantInfoRetriever
- IEnchantExecutor
- EnchantRegisterer

If you wanted to change enchants from being formatted like "DefenseEnchant 3" to "DefenseEnchant - 3", you could do so by swapping out the default
IEnchantInfoParser with your own.

In order to swap dependencies, we'll need to create our new implementation, and create a new Runnable which preforms the dependency swap.

```java
    private void InjectSpecialFormatting(){

        EasyEnchants easyEnchants = EasyEnchants.getInstance();
        Runnable dependencySwapTask = new Runnable() {
            @Override
            public void run() {

                IEnchantInfoParser enchantInfoParser = new IEnchantInfoParser() {

                    IEnchantInfoParser defaultParser = easyEnchants.dependencyRetriever.getEnchantInfoParser();

                    @Override
                    public EnchantInfo getEnchantInfo(String enchantString) {
                        return defaultParser.getEnchantInfo(enchantString.replace(" - ", " "));
                    }

                    @Override
                    public String createEnchantString(EnchantInfo enchantInfo) {
                        return enchantInfo.name + " - " + enchantInfo.level;
                    }
                };

                class DependencySwapDecorator extends DependencyCreatorDecorator{

                    public DependencySwapDecorator(IDependencyCreator dependencyCreator) {
                        super(dependencyCreator);
                    }

                    @Override
                    public IEnchantInfoParser getEnchantInfoParser() {
                        return enchantInfoParser;
                    }
                }

                easyEnchants.dependencyRetriever = new DependencySwapDecorator(easyEnchants.dependencyRetriever);
            }
        };

        EasyEnchants.dependencyBuilder.addTask(dependencySwapTask, 5);

    }

```

**
Note: If you try and access EasyEnchants.dependencyRetriever outside of the runnable, you may run into a null refference exception. This is because the IDependencyCreator object isn't created until after onEnable(). 
This also means any objects you create which are constructed with an object from EasyEnchants.dependencyRetriever should be created inside the runnable.
**

Note 2: All of the interfaces are implemented inline to make it easier to view this code on github. It is advisable to implement all interfaces / classes
in their own class files to keep your code clean.



