# Cryptography goals

Things that we want to achieve with cryptography:

* Passing secret data between computers (encryption)
* Checking data integrity & authenticity (Digital Signatures, Message Authenticity Codes) - the fact that no one
in the middle changed the message or sent it on behalf of someone else
* [Non-repudiation](https://en.wikipedia.org/wiki/Non-repudiation) - we won't cover this

These are used in various networking protocols like SSH, HTTPS, VPN, SSMTP. Some of them will be covered in the next
sections. 

# Symmetric key

Encryption turns useful information into unrecognizable array of bytes. Here is a simple algorithm (XOR):

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
Notice that decrypted value is the same as original data. This type of encryption is called **symmetric** because 
the same key (cypher) is used both for encryption and decryption.

## AES, 3DES

While XOR can be used in some protocols, for encryption it's considered weak and more elaborate algorithms 
like AES, DES (also can be broken), 3DES, etc are used. To the best of my knowledge AES is the choice #1 these days. 

## Block ciphers and their modes

Let's consider XOR (though the same is applicable to AES and many others). The key and the data usually don't
match in size, so if the key is size=n, then you split the data into blocks of
`n` bytes of the data and run XOR with them. Then take next block and run XOR with the same key and so on.

There are [many modes](https://en.wikipedia.org/wiki/Block_cipher_mode_of_operation) in which such cipher can
be used - the one describe before is called ECB (Electronic Codebook) and is not really secure. When encrypting
words "mama" and "papa" while the result won't be text it will posses same patterns because letter "a" 
results in the same byte sequence in both words. By these patterns you can deduce what the text was because the
frequency with which letters appear in the words are known for typical languages.

The situation can be further improved by changing the key from block to block. E.g. with CBC 
(Cipher Block Chaining) the encrypted data from previous block is used to build a key for the next block.
This [diffuses](https://en.wikipedia.org/wiki/Confusion_and_diffusion) the result not allowing to guess 
original patterns. 

Though you still need a cipher for the 1st block - in such context it's called IV
([initialization vector](https://en.wikipedia.org/wiki/Initialization_vector)).

# Asymmetric key

Symmetric keys have a conceptual problem - how do you share them? A usual case when you need encryption is passing data 
between 2 computers on the Internet and you don't want someone (like an internet provider) to eavesdrop:

```
 _____                                                                         _____  
|     |                                                                       |     |  
|  A  | -->Internet Provider 1-->Internet Provider 2-->Internet Provider N--> |  B  | 
|_____|                                                                       |_____|
```

But how do you transfer that key and at the same time prevent your Internet Provider from seeing it? 
If man-in-the-middle sees the key he'll be able to use it to decrypt everything.

This is where **asymmetric** keys can be used, now there are 2 of them:

* Public Key - can encrypt, but it can't decrypt
* Private Key - can decrypt

The steps are:

1. Transfer Public Key from B to A
2. Encrypt message on host A with that Public Key
3. Send the message to B
4. Use Private Key to decrypt the message

As you can see Public Key can be freely distributed - it doesn't matter if an eavesdropper has it because it can't
be used to decrypt. But Private Key has to be known _only_ by B.

There several algorithms that can be used for asymmetric key encryption:

* RSA is probably the most known and it's still the most widespread. It was the 1st of the kind. It depends on the 
fact that numbers can't be easily factored. So you could generate 2 huge prime numbers and multiply
them, and then even if you spread the information about their product no one can get the original 2 numbers from it.
Of course you can still calculate the public key from private one (multiply prime numbers again), but not vice versa.
* ECC (elliptic curve cryptography)

# Digital Signatures

Asymmetric keys can be used for something other than encryption. Say someone sent you a message and encrypted it. While
a man-in-the-middle cannot see the original content - he can still change bytes in the message.

RSA, DSA, ECDSA

# Forward secrecy, ephemeral keys

Here's a problem with asymmetric keys when used to exchange symmetric keys: 

1. An eavesdropper sees all the traffic including original exchange of the symmetric key and all the
subsequent data exchange secured by that key. Everything is gibberish of course, but all this communication
can be stored anyway.
2. In a year hackers (or government?) finds a way to get their hands your private key
3. Because they stored all the communication now they can decode your symmetric key and everything that was encrypted by it

What you could do to improve the situation is to use **ephemeral key exchange**:

1. Generate a short-living key pair, distribute public key (sign it with the long-living key)
2. Encrypt a symmetric key with the short-living public key, decrypt it on the other side
3. Forget short-living keys and don't log them anywhere

This way even if your private key is exposed later on, it wasn't used for encryption of the symmetric key and thus 
the latter can't be decrypted.

While this is a working scheme, there are easier and quicker ways of doing original key exchange and in
practice **asymmetric encryption is not used in two-way communication** anymore.  

# Sharing symmetric key using DH

Diffie–Hellman (DH) key exchange is a way of sharing a secret number over a public network. First, you should recall from 
school basic rules for powers: `(xᵃ)ᵇ=xᵃᵇ=xᵇᵃ`. So the idea is that `a` & `b` are private while `xᵃ` and `xᵇ` are 
shared, and after that each of the party finishes the expression by using their own private powers `(xᵃ)ᵇ=xᵃᵇ=xᵇᵃ`:

1. A or B agree on a number `x` that can be known to anyone 
2. A generates number `a` and sends `a1=xᵃ` to B
3. B generates number `b` and sends `b1=xᵇ` to A
4. A calculates `b1ᵃ` and B calculates `a1ᵇ` **which are equal** because `(xᵃ)ᵇ=xᵃᵇ=xᵇᵃ`

So even if there is a man-in-the-middle - he can only see `x`, `a1`, `b1`, but he doesn't know the eventual number.

## Powers and logarithms

Given `xᵃ=z` and the fact that `x` and `z` are known how _can_ you find `a`? The brute force approach would be 
to multiply `x` as many times as needed to reach `z`:

```
a = 2;
for(current = x; current != z; current *= x) 
  a++;
System.out.println(a);
```

We agreed before that if `z` is huge it's not feasible to find `a` this way. But... how did we calculate `xᵃ` 
in the first place? Wouldn't it take the same algorithm to raise `x` to a power?

It's true that calculating `z=xᵃ` is impractical, but we can do it smarter. E.g. to calculate `x¹²` we could:

```
x_2  = x * x
x_4  = x_2 * x_2
x_8  = x_4 * x_4
x_12 = x_8 * x_4
```

It took us 4 multiplications instead of 12! Such algorithm can allow us raise `x` to very large powers. And that's
all great, but couldn't hackers do a similar trick when searching for `a`? Do something like a binary search - 
first raise `x` to something huge and if it's larger than `z` try a smaller power and so on.

## Modular Arithmetic & Discrete Logarithms

In order to protect ourselves from the "binary search" we need to introduce our last complication - 
instead of distributing `z=xᵃ` we'll distribute `z=xᵃ % n`. 

Another name for Modular Arithmetic is Clock Arithmetic. An ordinary hour hand on a clock has 12 values - 0..11.
After the arrow crossed 11 it overflows back to 0. So in the expression `x % n` our `n` is the max value after
which we overflow. 

So while `z=xᵃ` can be binary-searched for `a`, with `z=xᵃ % n` you can't really check if your number is greater 
than `z` because it already overflowed multiple times. This makes it impossible to do binary search and there's 
no known way of guessing `a` quickly - this is called a 
[Discrete Logarithm](https://en.wikipedia.org/wiki/Discrete_logarithm) problem. 