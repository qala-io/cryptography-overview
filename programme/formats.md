# ASN.1

This is a generic format with which you can describe any structure. The idea is the same as in 
[Google Protobuf](https://developers.google.com/protocol-buffers). Here's an example of User structure:

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

Which roughly correspond to a class like this:

```
class User {
    String firstName, lastName;
    Role role;
    Address address;
}
```

You can define ASN.1 structure and then compile Java, C++, etc. types. These types can then be serialized into
different formats: JSON, XML, DER. So you can:
 
1. Create an object in Java, serialize it into DER
2. Send it to a software written in C++
3. Deserialize it into C++ object

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

## DER format

DER is a very(!) compact binary representation of ASN.1 object. 