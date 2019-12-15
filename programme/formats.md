# ASN.1

ASN.1 is a generic format with which you can describe any structure by defining a scheme and a message that conforms 
to that scheme. The idea is the same as in [Google Protobuf](https://developers.google.com/protocol-buffers) or 
in XSD+XML. Here's an example of User scheme:

```
User
DEFINITIONS
AUTOMATIC TAGS ::=
BEGIN	
	Address ::= SEQUENCE {
		city UTF8String (SIZE(1..32)),
		street UTF8String (SIZE(1..32))
	}
	
	User ::= SEQUENCE {
		firstName UTF8String (SIZE(1..16)),
		lastName UTF8String (SIZE(1..16)),
		role ENUMERATED { admin, user },
		address Address
	}
	
END
```

SEQUENCE bears the same meaning as XSD's sequence - it's just an ordered set of fields. Such schema roughly 
correspond to a class like this:

```
class User {
    String firstName, lastName;
    Role role;
    Address address;
}
```

Many programming platforms have libraries to automatically compile ASN.1 to your Java/C++/whatever types. 
Once the schema is defined we can create messages that follow it. In ASN.1 it's called PDU (protocol data unit):

```
value User ::= {
  firstName "Jerry",
  lastName "Smith",
  role admin,
  address {
    city "A",
    street "B"
  }
}
``` 

While this is a text format, it's just one of the many. You can also serialize messages into JSON, XML, DER, etc. So 
the workflow then looks like this:

1. Create ASN.1 schema, compile it e.g. into Java & C++ classes 
2. Create an object in Java, serialize it into one of the formats supported by ASN.1
3. Send it to a software written e.g. in C++
4. Deserialize it into C++ object on the other end

You can play with ASN.1 (compile schema and create DPUs) using [this online tool](https://asn1.io/asn1playground/).
In Java you can read and write ASN.1 messages using Bouncy Castle.

## DER format

The most important serialization format that comes with ASN.1 is DER - it's a very compact binary representation 
of ASN.1 messages. This format is used by most of the crypto software to encode keys and certificates. E.g. our 
Jerry Smith message would take [only 27 bytes](./jerry-smith.PDU.der) (remember that it's binary). 

You can use
[this decoder](http://www.lapo.it/asn1js/#MBmABUplcnJ5gQVTbWl0aIIBAKMGgAFBgQFC) to see the content of the file.
Note that field names are replaced with indices. And "admin" role turned into `0`. We can't turn this into 
well-printed human-readable data because the tool doesn't have a complied ASN.1 scheme. Field and "enum" names
are not present in DER.

Even more compact encodings exist (BER, PER) but DER is the one used by most of the software.

## PEM format

While DER is very compact it's inconvenient when we need to copy-paste data - for that we need it in a text form.
The solution is as always - to encode it further into Base64. And if we add header and footer in the right form
we'll end up with PEM:

```
-----BEGIN OUR ENCODED DATA----
MBmABUplcnJ5gQVTbWl0aIIBAKMGgAFBgQFC
-----END OUR ENCODED DATA----
```

Oftentimes private keys and certificates are of this form. PEM can include metadata and multiple entries 
(will be useful for certificate chains):

```
This is metadata, I can put whatever I want
-----BEGIN OUR ENCODED DATA----
MBmABUplcnJ5gQVTbWl0aIIBAKMGgAFBgQFC
-----END OUR ENCODED DATA----

Next piece of PEM (actually it's the same data)
-----BEGIN OUR ENCODED DATA----
MBmABUplcnJ5gQVTbWl0aIIBAKMGgAFBgQFC
-----END OUR ENCODED DATA----
```

In practice instead of `BEGIN OUR ENCODED DATA` there's a description of which format the underlying ASN.1 structure
represent - it could be public keys, private keys, certificates, etc.

## Object Identifiers (OID) and Key-Values

You'll see below how ASN.1 is used to describe standard structures like keys and certificates. You'll notice that
instead of field names OIDs are used in crypto formats. These OIDs look like this `1.3.6.1.4.1.343`, and their 
interpretation (human readable names) is described in respective specs. Looks like an overengineering, but who
am I to criticize.

You'll [also notice](http://www.lapo.it/asn1js/#MIIF4DCCBMigAwIBAgIQDACTENIG2-M3VTWAEY3chzANBgkqhkiG9w0BAQsFADB1MQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMRkwFwYDVQQLExB3d3cuZGlnaWNlcnQuY29tMTQwMgYDVQQDEytEaWdpQ2VydCBTSEEyIEV4dGVuZGVkIFZhbGlkYXRpb24gU2VydmVyIENBMB4XDTE0MDQwODAwMDAwMFoXDTE2MDQxMjEyMDAwMFowgfAxHTAbBgNVBA8MFFByaXZhdGUgT3JnYW5pemF0aW9uMRMwEQYLKwYBBAGCNzwCAQMTAlVTMRkwFwYLKwYBBAGCNzwCAQITCERlbGF3YXJlMRAwDgYDVQQFEwc1MTU3NTUwMRcwFQYDVQQJEw41NDggNHRoIFN0cmVldDEOMAwGA1UEERMFOTQxMDcxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpDYWxpZm9ybmlhMRYwFAYDVQQHEw1TYW4gRnJhbmNpc2NvMRUwEwYDVQQKEwxHaXRIdWIsIEluYy4xEzARBgNVBAMTCmdpdGh1Yi5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCx1Nw8r_3zTu3BZ63myyLot-KrKPL33GJwCNEMr9YWaiGwNksXDTZjBK6_6iBRlWVm8r-5TaQMKev1FbHoNbNwEJTVG1m0Jg_Wg1dZneF8Cd3gE8pNb0Obzc-HOhWnhd1mg-2TDP4rbTgceYiQz61YGC1R0cKj8keMbzgJubjvTJMLy4OUh-rgo7XZe5trD0P5yu6ADSindvEl9ME1PPZ0rd5qM4J73P1LdqfC7vJqv6kkpl_nLnwO28N0c_p-xtjPYOs2ViG2wYq4JIJNeCS66R2hiqeHvmYlab--O3JuT-DkhSUIsZGJuNZ0ZXabLE9iH6H6Or6cJL-fyrDFwGeNAgMBAAGjggHuMIIB6jAfBgNVHSMEGDAWgBQ901Cl1qCt7vNKYApl0yHU-PjWDzAdBgNVHQ4EFgQUakOQfTuYFHJSlTqqKApD-FF-06YwJQYDVR0RBB4wHIIKZ2l0aHViLmNvbYIOd3d3LmdpdGh1Yi5jb20wDgYDVR0PAQH_BAQDAgWgMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjB1BgNVHR8EbjBsMDSgMqAwhi5odHRwOi8vY3JsMy5kaWdpY2VydC5jb20vc2hhMi1ldi1zZXJ2ZXItZzEuY3JsMDSgMqAwhi5odHRwOi8vY3JsNC5kaWdpY2VydC5jb20vc2hhMi1ldi1zZXJ2ZXItZzEuY3JsMEIGA1UdIAQ7MDkwNwYJYIZIAYb9bAIBMCowKAYIKwYBBQUHAgEWHGh0dHBzOi8vd3d3LmRpZ2ljZXJ0LmNvbS9DUFMwgYgGCCsGAQUFBwEBBHwwejAkBggrBgEFBQcwAYYYaHR0cDovL29jc3AuZGlnaWNlcnQuY29tMFIGCCsGAQUFBzAChkZodHRwOi8vY2FjZXJ0cy5kaWdpY2VydC5jb20vRGlnaUNlcnRTSEEyRXh0ZW5kZWRWYWxpZGF0aW9uU2VydmVyQ0EuY3J0MAwGA1UdEwEB_wQCMAAwDQYJKoZIhvcNAQELBQADggEBAG_nbcuC8--QhwnXDxUiLIz-06scipbbXRJd0XjAMbD_RciJ9wiYUhcfTEsgZGpt21DXEL5-q_4vgNipSlhBaYFyGQiDm5IQTmIte0ZwQ26jUxMf4pOmI1v3kj43FHU7uUskQS6lPUgND5nqHkKXxv6V2qtHmssrA9YNQMEK93ga2rWDpK21mUkgLviTPB5sPdE7IzprOCp-Ynpf3RcFddAkXb6NqJoQRPrStMrv19C1dqUmJRwIQdhkkqevff6IQDlhC8BIMKmCNK33cEYDfDWROtW7JNgBvBTwww8jO1gyug8SbGZ6bZ3k8OV8XX4C2NesiZcLYbc2n7B9O-63M2k)
that there are many fields that look like this: 

```
SEQUENCE 
   OBJECT IDENTIFIER 2.5.4.6
   PrintableString US
```

This is a key-value where first we see an OID `2.5.4.6` (which is a country name) and then a string "US".
Crypto formats heavily use sets of such key-values for extensibility: first a spec is prepared that describes
super important fields (their OIDs), and then new fields can be added by other specifications that describe
less important parts.   

# X.509 aka PKIX

X.509 is a standard that defines ASN.1 scheme for certificates. There is already 
[a great article](https://cipherious.wordpress.com/2013/05/13/constructing-an-x-509-certificate-using-asn-1/)
that describes what a certificate consists of, so I won't duplicate it.

Such cert could be stored as PEM in which case it would have headers:

```
-----BEGIN CERTIFICATE-----
```

# PKCS

PKCS is a number of specifications that describe standard ASN.1 schemes to store crypto data:

* PKCS#1 - public or private RSA key 
* PKCS#8 - any type (including RSA) of public or private key
* [CMS](https://tools.ietf.org/html/rfc5652) (based on older PKCS#7) - certificate like X.509
* PKCS#12 - certificate like X.509 _and its private key_ 

If you see a PEM file with some header and you want to know what format is encoded there, you can find some
of the common headers and their descriptions in 
[this article](https://tls.mbed.org/kb/cryptography/asn1-key-structures-in-der-and-pem).  