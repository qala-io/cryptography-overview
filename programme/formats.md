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
Unfortunately many of the SDKs and editors to work with ASN.1 are not free.

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

# X.509 aka PKIX

X.509 is a standard that defines ASN.1 scheme for certificates. Of course such cert could be stored as PEM
in which case it would have headers:

```
-----BEGIN CERTIFICATE-----
``` 

# PKCS

PKCS is a number of specifications that describe standard ASN.1 schemes to store crypto data:

* PKCS#1 - public or private RSA key 
* PKCS#8 - any generic (including RSA) public or private key
* PKCS#7 - certificate like X.509
* PKCS#12 - certificate like X.509 _and its private key_ 

If you see a PEM file with some header and you want to know what format is encoded there, you can find some
of the common headers and their descriptions in 
[this article](https://tls.mbed.org/kb/cryptography/asn1-key-structures-in-der-and-pem).  