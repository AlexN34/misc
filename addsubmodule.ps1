param (
    [string] $path = $null # Plugin url containing plugin urls
)
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

#to finish, add module to plugin list
Add-Content .\pluginURLs.txt "`r`n$path"

