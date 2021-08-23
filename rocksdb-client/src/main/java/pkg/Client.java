package pkg;

import org.rocksdb.*;

public class Client {
    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) throws RocksDBException {
        Options options = new Options();
        options.setCreateIfMissing(true);
        RocksDB db = RocksDB.open(options, "/tmp/rocks");
        byte[] key = new byte[1];
        key[0] = 1;
        byte[] value = new byte[2];
        value[0] = 2;
        value[1] = 4;
//        db.put(key, value);

        String path = "/tmp/backup-rocks";
        try (BackupableDBOptions backupableDBOptions = new BackupableDBOptions(path)) {
            BackupEngine backupEngine = BackupEngine.open(Env.getDefault(), backupableDBOptions);
//            backupEngine.createNewBackup(db);
            backupEngine.restoreDbFromBackup(1, path, path, new RestoreOptions(false));
        }
        byte[] bytes = db.get(key);
        System.out.println(bytes[0]);
        System.out.println(bytes[1]);
        db.close();
    }
}
