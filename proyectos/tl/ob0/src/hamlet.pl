# Gabriel Centurion	gccomputos@gmail.com
# 
undef $/;
$text = <STDIN>;
$text =~ s/(\S+?) (\S+?) (\S+?) (\S+?) (.*+)/$1 $2 $3 $4 .../g;
print $text