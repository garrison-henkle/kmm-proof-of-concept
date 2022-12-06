// swift-tools-version:5.3
import PackageDescription

let remoteKotlinUrl = "https://api.github.com/repos/garrison-henkle/kmm-proof-of-concept/releases/assets/87249737.zip"
let remoteKotlinChecksum = "9bb621c77b9303448dad3ad3c97887adaeb513c7b6ed8603bc4fde9ecae17dba"
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