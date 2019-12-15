package io.qala.crypto.format;

import org.junit.Test;

public class AsnTest {
    @Test public void asnToString() {
        System.out.println(new Asn(new Pem("-----BEGIN RSA PRIVATE KEY-----\n" +
                "MIIEpQIBAAKCAQEA5ijarH57Fwndb3ci4c9g1EuLkuHkJXrUWHvyPvW9+qOUN74D\n" +
                "fWBMzCc2kA2OTRpsZ2Unaz/zhI3b/MiaXPCgndySNiBbMEqunDK12RddNy61JxLc\n" +
                "o90sgZzSKtCBndZ+c70oq7Uy5+ZbUkA+XfErl8dEh8hdwF2UQk9ZARlTqZRmuM33\n" +
                "4VXDD8VcqC2PkdULxrIUyGKXwi5gSGZApym9Qb0glYa/fXP1A1Q4cQaY3k43tk0+\n" +
                "535Gdf5ljF5SOMRodyVWozQhObPNBxy9TlR4Wmprdel0SEDnj6gvG4TP2dEfzwec\n" +
                "wFnSGLYGEX7s3YQLUPMQrJ0fXYOpcPHyJhWnMwIDAQABAoIBAFNj3/75gJ2TTHCz\n" +
                "aKcKHRouGO7p+2B9BWzd06JuJRwnom8j4F3Pw2OAnatJzhIvjbFUnBimvhN71oXb\n" +
                "JNqhsIAhx7Pmu0Ne+67KTMrQRUzWFnqGwfx5CUQYzqkGjL/kVEKY+aBk8YHGG7ZM\n" +
                "Yoxk8hd28mSD+zvUbAAugZ/9Tiu1UjDTaTksFgQmKjCiy4dMCLcoT6EQLrnBX51w\n" +
                "tnrH7MPc/GyJaBtzCir3WU84KDx4lclXvejYapEzW70/yorhYyHxSuUamMvm9gP1\n" +
                "PJaAqJQSurRVPNOuhYW/Ksc8jcjAuiLpw80o9MHlfMMCZUza0CEEgUFwurZHBBLY\n" +
                "NGZ9B2ECgYEA84yZIe486ln4HXjredWIK080SZ4CppOxCKP7Dozhf44IqJFMgHtb\n" +
                "T4yfw5Mdp8ejVQexYM9RrV/qE+ZjEt2O4BygirRk0MLtHlXo80/i5kKJeqHZFULd\n" +
                "8UqmXFq1AF3tLdwvWmocy8sU5c+tI4fU31vIVMhYLMAcMuUPLWvCuyMCgYEA8e0F\n" +
                "n8+R0g489UgQ3euR4yNw03wOKHfl2fp4izF9YvQPi6H4JnuDRBOvRIzRwdlpG0M+\n" +
                "acqGPUcMZ/sB1kk80hJuUcyH8Zg7mwczi8R975SBmBOkM5WaaFe8yRP8OpePbq4u\n" +
                "JTe1ZdZSYjjWR2WTFjl9z6H3Cn7EUWKUeP467LECgYEA10H5RqgOZwNF7KanWXOF\n" +
                "euHyUO67YQdAfWaRyvZxiA+9T1+8wgJFXjXoLy+kElq83baC7BHRGu+fMB98iXwR\n" +
                "f7tNmcp/IJRoh1tfHU4KOYGtiRnriIMlLtLP3ui8+aoKq3Hk2IDZGrZPrh6z5Kd1\n" +
                "WkUA9w5sAIF7avBRTc/kuQkCgYEAupSCce40GDzC5qjd2UmFChibO3/Bq5RMwQBb\n" +
                "V36jpV11X8tbBtLbOfW0hrnUTBzQ4yXJTO3U+g2Bk/ASSqHRNnLgglWrwrgVwEqd\n" +
                "hn3UYqG2EQOU7/PBUhfYkXvIW6foBwRusmV0kUXp47bSC95awhk03p2bWYzK+7l7\n" +
                "qJTp/QECgYEA5E43R6gA88DZk3sHv/TklpxF7K7n+zo1C0u32ZAYl42ypbZ3XMcy\n" +
                "Y4+uuMTg/IACuk/3PGSceE+AOXMMbWc2Ze9TOOHJSPZhgUsfYlAyEOfVi7nuzQ6j\n" +
                "0gTxOgHGZMCSgi5oo6k3nfkV6L+6k8BIueOb/zjofqLghVuBFmd5OsY=\n" +
                "-----END RSA PRIVATE KEY-----\n").toDer()).toString());
    }
}