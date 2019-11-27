# Symmetric key

Encryption turns useful information into unrecognizable array of bytes. Here is a simple algorithm:

1. Take a string "Hi" which has a binary representation `0100100001101001`
2. Generate a cypher (key) for encryption (just random bytes): `0001110011111111`
3. Run XOR operation between them, this is your encryption:

```
0100100001101001 (original data)
0001110011111111 (cypher)
0101010010010110 (encrypted)
```

4. To decrypt it back run XOR operation between the encrypted data and cypher: 
```
0001110011111111 (cypher)
0101010010010110 (encrypted)
0100100001101001 (decrypted)
```
Notice that decrypted value is the same as original data.

Of course the key and the data usually don't match in size, so if the key is size=n, then you take first n bytes of
the data and run XOR with them. Then take next n bytes and run XOR with the same key and so on.

This type of encryption is called **symmetric** because the same key (cypher) is used both for encryption and decryption.

In real life such simple XOR-algorithm can be easily broken for text data with a brute force:

1. Start generating XOR cyphers starting from 0 and keep incrementing
2. Run XOR and when you see a lot of english letters - you probably guessed the cypher

So this algorithm of symmetric encryption is not safe, there are much more elaborate algorithms like AES, 
DES (also can be broken), etc. To the best of my knowledge AES is the choice #1 these days. 

# Asymmetric key

Symmetric keys have a conceptual problem - how do you share them? A usual case when you need encryption is passing data 
between 2 computers on the Internet and you don't want someone (like internet provider) to eavesdrop 
(man-in-the-middle attack):

```
 _____                                                                         _____  
|     |                                                                       |     |  
|  A  | -->Internet Provider 1-->Internet Provider 2-->Internet Provider N--> |  B  | 
|_____|                                                                       |_____|
```

But how do you transfer that key and at the same time prevent your Internet Provider from seeing it? 
If man-in-the-middle sees the key he'll be able to use it to decrypt everything.

This is where **asymmetric** keys are used, now there are 2 of them:

* Public Key - can encrypt, but it can't decrypt
* Private Key - can decrypt

Now the steps are:

1. Transfer Public Key from B to A
2. Encrypt message on host A with that Public Key
3. Send the message to B
4. Use Private Key to decrypt the message

As you can see Public Key can be freely distributed - it doesn't matter if man-in-the-middle has it because it can't
be used to decrypt. But Private Key has to be known _only_ by B.

There several algorithms that can be used for asymmetric key encryption:

* RSA is probably the most known and it's still the most widespread. It was the 1st of the kind. It uses Number Theory
and depends on the fact that numbers can't be easily factored. So you could generate 2 huge prime numbers and multiply
them, and then even if you spread the information about their product you can't get the original 2 numbers of it.
Of course you can still calculate the public key from private one (multiply prime numbers again), but not vice versa.
* DSA
* ECC (elliptic curve cryptography) 