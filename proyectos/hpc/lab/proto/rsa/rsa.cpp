#include "openssl/sha.h"
#include "openssl/rsa.h"
#include "openssl/pem.h"
#include "string.h"
#include "assert.h"

//-Lopenssl

// get 64-byte signature from its base64 encoded version
int unbase64(char *input, unsigned char **out)
{
   BIO *b64, *bmem;

   // base64 encoding is longer than the original 'string'
   int length = strlen(input);
   unsigned char *buffer = (unsigned char *)malloc(length);

   b64 = BIO_new(BIO_f_base64());
   BIO_set_flags(b64, BIO_FLAGS_BASE64_NO_NL);

   bmem = BIO_new_mem_buf(input, length);
   bmem = BIO_push(b64, bmem);

   length = BIO_read(bmem, buffer, length);
   buffer[length] = 0;

   BIO_free_all(bmem);

   *out = buffer; // caller frees
   return length;
}

// verify plaintext key is matching signature (base64 encoded)
int VerifyKey(char* key, char* sig_b64)
{
   unsigned char hash[SHA_DIGEST_LENGTH/*20*/];

   char pub_key[] = {
// PEM format just like the public key file saved by openssl
"-----BEGIN PUBLIC KEY-----\n"
"MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJ9mjRfj3xkXNAIdvk/QVi+xJruhn74u\n"
"XH2fPz8tOK1sJx+zI64bwchdFThqlf368Yid9CRjhni/VpYUeH5OpJkCAwEAAQ==\n"
"-----END PUBLIC KEY-----\n"
   };

   unsigned char* signature;
   int length = unbase64(sig_b64, &signature);

   // WARNING: no error checking for brevity
   BIO* bio = BIO_new_mem_buf(pub_key, sizeof(pub_key));

   RSA* rsa_key = 0;
   PEM_read_bio_RSA_PUBKEY(bio, &rsa_key, 0,0); 
   assert(64 == RSA_size(rsa_key)); // 512 bits (/8=64)

   SHA1((unsigned char*)key, strlen(key), hash);
   // padding fixed to PKCS1 v1.5 (shortcut to RSA_public_decrypt)
   int ret = RSA_verify(NID_sha1, hash, 20, signature, length, rsa_key);

   RSA_free(rsa_key);
   BIO_free(bio);
   free(signature);

   return ret;
}

int main(void) 
{
   char *key = "1.001|KT.1|1|26.06.2010|0|Joe Bloggs|some@email.com|";
   char* sig64 = 
   "OW5wH10Jgz6F+f+cCHE5gzzSCtWPw5RTlhXrfurtus+uk6z5UjrIaM+W+4AZYObI8o5oC8xdDCLafNNmstsOww==";

   if(VerifyKey(key, sig64)) 
      printf("key OK!\n");

   // if the key is tampered...
   char *ke2 = "1.001|KT.1|1|26.06.2010|0|JIM Bloggs|some@email.com|";
   if(!VerifyKey(ke2, sig64)) // verification fails!
      printf("dodgy key!\n");
}
