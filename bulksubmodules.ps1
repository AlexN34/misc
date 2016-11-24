param (
    [string] $path = $null # Path to textfile containing plugin urls
)

$textfile = Get-Content $path #separate by \n's
foreach ($elt in $textfile) {
# add Common.git as a submodule at the path "common" inside this repo
    git submodule add $elt
# initialize it, clone, and check out a copy
    git submodule update --init
# commit the addition of the submodule
    git commit -m "added $elt"    
}
#pull anything not yet updated
git pull origin windowsvim
#update to master repo
git push

