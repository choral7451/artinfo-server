import {Module} from "@nestjs/common";
import {PrismaService} from "@/apis/integration/prisma.service";
import {AuthService} from "@/apis/auth/auth.service";
import {AuthResolver} from "@/apis/auth/auth.resolver";
import {UserService} from "@/apis/user/user.service";
import {JwtModule, JwtService} from "@nestjs/jwt";

@Module({
  imports: [JwtModule.register({})],
  providers:[AuthResolver, JwtService, PrismaService, AuthService, UserService]
})
export class AuthModule {}