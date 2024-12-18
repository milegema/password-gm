package com.bitwormhole.passwordgm.security;

import com.bitwormhole.passwordgm.data.ids.KeyAlias;
import com.bitwormhole.passwordgm.data.ids.UserAlias;

public interface KeyPairManager {

    // methods

    KeyPairHolder get(KeyAlias alias);

    KeyPairHolder get(UserAlias alias);

    KeyPairHolder getRoot();

    KeyAlias[] listAliases();

    // agent

    public class Agent {
        public static KeyPairManager getKeyPairManager() {
            KeyPairManager man = inst;
            if (man == null) {
                man = new KeyPairManagerImpl();
                inst = man;
            }
            return man;
        }

        private static KeyPairManager inst;
    }
}
