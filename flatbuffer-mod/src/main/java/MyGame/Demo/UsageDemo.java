package MyGame.Demo;

import MyGame.Sample.*;
import com.google.flatbuffers.FlatBufferBuilder;

public class UsageDemo {
    public static void main(String[] args) {
        // Create a `FlatBufferBuilder`, which will be used to create our monsters' FlatBuffers.
        FlatBufferBuilder builder = new FlatBufferBuilder(1024);
        int weaponOneName = builder.createString("Sword");
        short weaponOneDamage = 3;
        int weaponTwoName = builder.createString("Axe");
        short weaponTwoDamage = 5;
        // Use the `createWeapon()` helper function to create the weapons, since we set every field.
        int sword = Weapon.createWeapon(builder, weaponOneName, weaponOneDamage);
        int axe = Weapon.createWeapon(builder, weaponTwoName, weaponTwoDamage);

        // Serialize a name for our monster, called "Orc".
        int name = builder.createString("Orc");
        // Create a `vector` representing the inventory of the Orc. Each number
        // could correspond to an item that can be claimed after he is slain.
        byte[] treasure = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int inv = Monster.createInventoryVector(builder, treasure);


        // Place the two weapons into an array, and pass it to the `createWeaponsVector()` method to
        // create a FlatBuffer vector.
        int[] weaps = new int[2];
        weaps[0] = sword;
        weaps[1] = axe;
        // Pass the `weaps` array into the `createWeaponsVector()` method to create a FlatBuffer vector.
        int weapons = Monster.createWeaponsVector(builder, weaps);


        Monster.startPathVector(builder, 2);
        Vec3.createVec3(builder, 1.0f, 2.0f, 3.0f);
        Vec3.createVec3(builder, 4.0f, 5.0f, 6.0f);
        int path = builder.endVector();

        int pos = Vec3.createVec3(builder, 1.0f, 2.0f, 3.0f);

        // Create our monster using `startMonster()` and `endMonster()`.
        Monster.startMonster(builder);
        Monster.addPos(builder, pos);
        Monster.addName(builder, name);
        Monster.addColor(builder, Color.Red);
        Monster.addHp(builder, (short) 300);
        Monster.addInventory(builder, inv);
        Monster.addWeapons(builder, weapons);
        Monster.addEquippedType(builder, Equipment.Weapon);
        Monster.addEquipped(builder, axe);
        Monster.addPath(builder, path);
        int orc = Monster.endMonster(builder);
        // Call `finish()` to instruct the builder that this monster is complete.
        builder.finish(orc); // You could also call `Monster.finishMonsterBuffer(builder, orc);`.


        // This must be called after `finish()`.
        java.nio.ByteBuffer buf = builder.dataBuffer();
        // move to front
        buf.compact();
        // ready for reading
        buf.flip();
        deserialize(buf);


        // The data in this ByteBuffer does NOT start at 0, but at buf.position().
        // The number of bytes is buf.remaining().
        // Alternatively this copies the above data out of the ByteBuffer for you:
//        byte[] byteArray = builder.sizedByteArray();

//        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
//        System.out.println(byteBuffer.hashCode());
//        deserialize(byteBuffer);
    }

    private static void deserialize(java.nio.ByteBuffer buf) {
        Monster monster = Monster.getRootAsMonster(buf);
        short hp = monster.hp();
        short mana = monster.mana();
        String name = monster.name();

        Vec3 pos = monster.pos();
        float x = pos.x();
        float y = pos.y();
        float z = pos.z();

        int invLength = monster.inventoryLength();
        int thirdItem = monster.inventory(2);

        int weaponsLength = monster.weaponsLength();
        String secondWeaponName = monster.weapons(1).name();
        short secondWeaponDamage = monster.weapons(1).damage();

        int unionType = monster.equippedType();
        if (unionType == Equipment.Weapon) {
            Weapon weapon = (Weapon) monster.equipped(new Weapon()); // Requires explicit cast
            // to `Weapon`.
            String weaponName = weapon.name();    // "Axe"
            short weaponDamage = weapon.damage(); // 5
        }
    }
}
