// swift-tools-version:5.3
import PackageDescription

let remoteKotlinUrl = "https://api.github.com/repos/garrison-henkle/kmm-proof-of-concept/releases/assets/87086919.zip"
let remoteKotlinChecksum = "1ae4629dfb384c99e8d03cb83d72049348987e69b8735f8b76ac2b0e79c14966"
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