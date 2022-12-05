// swift-tools-version:5.3
import PackageDescription

let remoteKotlinUrl = "https://api.github.com/repos/garrison-henkle/kmm-proof-of-concept/releases/assets/87099028.zip"
let remoteKotlinChecksum = "333fcf228c288fafdad576bf33cf3de919fcabc14f58ff74befde8a97a8b9962"
let packageName = "shared"

let package = Package(
    name: packageName,
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(
            name: packageName,
            targets: [packageName]
        ),
    ],
    targets: [
        .binaryTarget(
            name: packageName,
            url: remoteKotlinUrl,
            checksum: remoteKotlinChecksum
        )
        ,
    ]
)