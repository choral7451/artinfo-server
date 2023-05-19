import {Module} from "@nestjs/common";
import {UserResolver} from "./user.resolver";
import {PrismaService} from "@/apis/integration/prisma.service";
import {UserService} from "@/apis/user/user.service";

@Module({
  providers:[UserResolver, PrismaService, UserService]
})
export class UserModule {}